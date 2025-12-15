# 🔍 ANÁLISIS: Implementación de Subida de Archivos

## Estado Actual vs Requisitos Futuros

### ✅ BIEN Implementado
- FileStorageService con UUID (previene colisiones)
- StringUtils.cleanPath() sanitiza nombres
- WebConfig sirve archivos estáticos en /uploads/**
- Validación de path traversal (..)
- Límite de 5MB configurado

---

## ⚠️ PROBLEMAS CRÍTICOS PARA AWS EC2

### 1. **Ruta relativa `uploads/` NO funcionará en EC2**
```properties
# ❌ ACTUAL (relativo al directorio de ejecución)
app.upload.dir=uploads

# ✅ DEBE SER (ruta absoluta configurable)
app.upload.dir=${APP_UPLOAD_DIR:/home/ubuntu/visso-uploads}
```

**¿Por qué?**
- En desarrollo: ejecutas desde IDE, la carpeta `uploads/` se crea donde está el proyecto
- En EC2: ejecutas con `java -jar`, la carpeta se crea en `/home/ubuntu/` o donde ejecutes
- Si reinicias la app o cambias de directorio, PIERDES LOS ARCHIVOS ❌

**Solución**: Usar variable de entorno o ruta absoluta fija.

---

### 2. **ProductoController NO asocia imagen con categoría/marca**
```java
// ❌ ACTUAL: Creas producto pero NO asignas Categoria ni Marca
producto.setCodigoProducto(codigoProducto);
producto.setNombre(nombre);
// ... falta categoria y marca

// ✅ DEBE SER:
Categoria categoria = categoriaService.obtenerPorId(categoriaId)
    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
producto.setCategoria(categoria);
producto.setMarca(marca);
```

**Impacto**: Fallarán las inserciones por `@JoinColumn(nullable = false)`.

---

### 3. **MarcaController NO tiene endpoint para crear/actualizar con imagen**
- ProductoController ✅ tiene POST/PUT con multipart
- MarcaController ❌ probablemente solo tiene endpoints JSON

**Necesitas**: Agregar endpoints multipart a MarcaController.

---

### 4. **Sin validación de tipos de archivo permitidos**
```java
// ❌ ACTUAL: Acepta CUALQUIER archivo (incluso .exe, .sh)
public String storeFile(MultipartFile file) {
    // ... guarda sin validar extensión
}

// ✅ DEBE SER:
private static final List<String> ALLOWED_EXTENSIONS = 
    Arrays.asList("jpg", "jpeg", "png", "gif", "webp", "avif");

public String storeFile(MultipartFile file) {
    String extension = getFileExtension(file.getOriginalFilename());
    if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
        throw new FileStorageException("Extensión no permitida: " + extension);
    }
    // ... resto del código
}
```

**Impacto seguridad**: Usuario malicioso podría subir scripts ejecutables.

---

### 5. **Sin manejo de imágenes antiguas al actualizar**
```java
// ❌ ACTUAL: Al actualizar producto con nueva imagen, la vieja queda huérfana
@PutMapping("/{id}")
public ResponseEntity<Producto> actualizarProducto(...) {
    if (imagen != null) {
        String fileName = fileStorageService.storeFile(imagen);
        producto.setImagenUrl(fileName); // ¡La imagen vieja NO se borra!
    }
}

// ✅ DEBE SER:
@PutMapping("/{id}")
public ResponseEntity<Producto> actualizarProducto(...) {
    Producto existente = productoService.obtenerPorId(id).orElseThrow();
    
    if (imagen != null && !imagen.isEmpty()) {
        // Borrar imagen antigua
        if (existente.getImagenUrl() != null) {
            fileStorageService.deleteFile(existente.getImagenUrl());
        }
        // Guardar nueva
        String fileName = fileStorageService.storeFile(imagen);
        producto.setImagenUrl(fileName);
    }
}
```

**Impacto**: Desperdicio de espacio en disco, miles de imágenes huérfanas en EC2.

---

## 🔐 PREPARACIÓN PARA JWT

### 6. **Endpoints sin protección**
```java
// ❌ ACTUAL: Cualquiera puede crear/modificar productos
@PostMapping
public ResponseEntity<Producto> crearProducto(...) { }

// ✅ CON JWT: Solo ADMIN puede crear
@PreAuthorize("hasRole('ADMIN')")
@PostMapping
public ResponseEntity<Producto> crearProducto(...) { }
```

**Preparación necesaria**:
1. Agregar `@PreAuthorize` a métodos sensibles
2. FileStorageService NO necesita cambios (se ejecuta tras validar JWT)
3. WebConfig sirve archivos públicamente (usuarios ven imágenes sin login) ✅

---

## 📦 PREPARACIÓN PARA DEPLOYMENT EN EC2

### 7. **Falta configuración de producción**
Crear `application-prod.properties`:
```properties
# Base de datos en EC2 (no localhost)
spring.datasource.url=jdbc:mysql://localhost:3306/visso_db
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD}

# Ruta absoluta para uploads
app.upload.dir=/home/ubuntu/visso-uploads

# Cambiar a validate (usar Flyway en producción)
spring.jpa.hibernate.ddl-auto=validate

# Desactivar logs SQL en producción
spring.jpa.show-sql=false

# JWT config
jwt.secret=${JWT_SECRET}
jwt.expiration=86400000
```

### 8. **Persistencia de archivos en EC2**
Opciones:
- **Opción A (Simple)**: Carpeta local persistente
  ```bash
  # En EC2, crear carpeta con permisos
  sudo mkdir -p /home/ubuntu/visso-uploads
  sudo chown ubuntu:ubuntu /home/ubuntu/visso-uploads
  sudo chmod 755 /home/ubuntu/visso-uploads
  ```
  
- **Opción B (Profesional)**: AWS S3 (futuro)
  - Requiere cambiar FileStorageService para usar AWS SDK
  - Ventajas: escalable, backups automáticos, CDN
  - Desventajas: más complejo, costos adicionales

**Recomendación**: Empezar con Opción A (carpeta local), migrar a S3 si el proyecto crece.

---

## 🎯 CHECKLIST DE CORRECCIONES NECESARIAS

### Prioridad ALTA (antes de deployment)
- [ ] Cambiar `app.upload.dir` a ruta absoluta o variable de entorno
- [ ] Agregar validación de extensiones permitidas en FileStorageService
- [ ] Completar ProductoController: asignar categoria y marca desde IDs
- [ ] Agregar manejo de imágenes antiguas al actualizar (deleteFile)
- [ ] Crear MarcaController con endpoints multipart
- [ ] Crear `application-prod.properties` con config de producción

### Prioridad MEDIA (para JWT)
- [ ] Agregar `@PreAuthorize("hasRole('ADMIN')")` en POST/PUT/DELETE
- [ ] Configurar excepciones personalizadas para 401/403
- [ ] Probar que `/uploads/**` sea accesible sin JWT (para usuarios)

### Prioridad BAJA (mejoras futuras)
- [ ] Implementar compresión de imágenes (reducir tamaño)
- [ ] Agregar logs de auditoría (quién subió qué archivo)
- [ ] Migrar a AWS S3 si escala el proyecto
- [ ] Implementar cache para imágenes frecuentes

---

## 🚀 ARQUITECTURA RECOMENDADA FINAL

```
┌─────────────────────────────────────────────────┐
│             AWS EC2 Instance                     │
│                                                  │
│  ┌──────────────────────────────────────────┐  │
│  │   Spring Boot App (puerto 8081)          │  │
│  │   - JWT Security habilitado              │  │
│  │   - FileStorageService activo            │  │
│  │   - WebConfig sirve /uploads/**          │  │
│  └──────────────┬───────────────────────────┘  │
│                 │                                │
│  ┌──────────────▼───────────────────────────┐  │
│  │   MySQL Database (puerto 3306)           │  │
│  │   - visso_db                             │  │
│  └──────────────────────────────────────────┘  │
│                                                  │
│  ┌──────────────────────────────────────────┐  │
│  │   /home/ubuntu/visso-uploads/            │  │
│  │   - UUID_imagen1.jpg                     │  │
│  │   - UUID_imagen2.png                     │  │
│  │   (persistente, chmod 755)               │  │
│  └──────────────────────────────────────────┘  │
└─────────────────────────────────────────────────┘
                    ▲
                    │ HTTPS/HTTP
                    │
        ┌───────────▼────────────┐
        │   React Frontend       │
        │   (GitHub Pages o      │
        │    Nginx en EC2)       │
        └────────────────────────┘
```

---

## ✅ CONCLUSIÓN

La implementación **base es correcta**, pero necesita **5 ajustes críticos** antes de JWT y EC2:

1. **Ruta absoluta** para uploads (variable de entorno)
2. **Validación de extensiones** (seguridad)
3. **Completar ProductoController** (asignar relaciones)
4. **Limpiar imágenes antiguas** al actualizar
5. **Crear application-prod.properties** con config de producción

**Tiempo estimado de correcciones**: 1-2 horas

¿Quieres que implemente estas correcciones ahora antes de seguir con JWT?
