-- ============================
-- CATEGORIAS (Sin tildes)
-- ============================
INSERT INTO categoria (id_categoria, nombre) VALUES
(1, 'Opticos'),
(2, 'Lentes de sol'),
(3, 'Lentes de contacto'),
(4, 'Accesorios');

-- ============================
-- MARCAS
-- ============================
INSERT INTO marca (id_marca, nombre, imagen) VALUES
(1, 'Ray-Ban', '/images/marcas/ray-ban.png'),
(2, 'Oakley', '/images/marcas/oakley.png'),
(3, 'Gucci', '/images/marcas/gucci.png'),
(4, 'Generico', '/images/marcas/generico.png');

-- ============================
-- PRODUCTOS
-- ============================
INSERT INTO producto (
    id_producto,
    codigo_producto,
    nombre,
    descripcion,
    precio,
    stock,
    fecha_creacion,
    imagen_url,
    id_categoria,
    id_marca
) VALUES
-- LENTES OPTICOS (Categoria 1)
(1, 'OPT-RB-01', 'Lente optico Ray-Ban clasico',
 'Lente optico con marco metalico elegante', 79990.00, 10, '2025-11-18', '/images/PRODUCTOS/OPTICOS/0_4.webp', 1, 1),

(2, 'OPT-RB-02', 'Lente optico Ray-Ban aviador',
 'Marco de acetato con diseno aviador', 85990.00, 8, '2025-11-18', '/images/PRODUCTOS/OPTICOS/o_1.webp', 1, 1),

(3, 'OPT-OA-01', 'Lente optico Oakley deportivo',
 'Marco ligero ideal para uso diario', 72990.00, 12, '2025-11-18', '/images/PRODUCTOS/OPTICOS/o_2.webp', 1, 2),

(4, 'OPT-GU-01', 'Lente optico Gucci premium',
 'Diseno de lujo con detalles dorados', 149990.00, 5, '2025-11-18', '/images/PRODUCTOS/OPTICOS/o_3.webp', 1, 3),

(5, 'OPT-RB-03', 'Lente optico Ray-Ban rectangular',
 'Marco rectangular versatil para todo rostro', 69990.00, 15, '2025-11-18', '/images/PRODUCTOS/OPTICOS/o_5.webp', 1, 1),

(6, 'OPT-GU-02', 'Lente optico Gucci redondo',
 'Estilo vintage con marco redondo', 139990.00, 7, '2025-11-18', '/images/PRODUCTOS/OPTICOS/o_6.webp', 1, 3),

(7, 'OPT-OA-02', 'Lente optico Oakley moderno',
 'Diseno contemporaneo con acabado mate', 78990.00, 10, '2025-11-18', '/images/PRODUCTOS/OPTICOS/o_7.webp', 1, 2),

-- LENTES DE SOL (Categoria 2)
(8, 'SOL-RB-01', 'Lente de sol Ray-Ban Wayfarer',
 'Modelo iconico con proteccion UV400', 89990.00, 20, '2025-11-18', '/images/PRODUCTOS/SOL/s_1.webp', 2, 1),

(9, 'SOL-OA-01', 'Lente de sol Oakley deportivo',
 'Lente polarizado ideal para deportes', 95990.00, 15, '2025-11-18', '/images/PRODUCTOS/SOL/s_2.webp', 2, 2),

(10, 'SOL-GU-01', 'Lente de sol Gucci fashion',
 'Diseno exclusivo de alta costura', 179990.00, 8, '2025-11-18', '/images/PRODUCTOS/SOL/s_3.webp', 2, 3),

(11, 'SOL-RB-02', 'Lente de sol Ray-Ban aviador',
 'Clasico aviador con lentes espejados', 99990.00, 18, '2025-11-18', '/images/PRODUCTOS/SOL/s_4.webp', 2, 1),

(12, 'SOL-OA-02', 'Lente de sol Oakley racing',
 'Tecnologia de lente avanzada para deportes extremos', 109990.00, 12, '2025-11-18', '/images/PRODUCTOS/SOL/s_5.webp', 2, 2),

(13, 'SOL-GU-02', 'Lente de sol Gucci oversized',
 'Montura grande estilo celebridad', 169990.00, 6, '2025-11-18', '/images/PRODUCTOS/SOL/s_6.webp', 2, 3),

(14, 'SOL-RB-03', 'Lente de sol Ray-Ban clubmaster',
 'Estilo retro con marco combinado', 92990.00, 14, '2025-11-18', '/images/PRODUCTOS/SOL/s_7.webp', 2, 1),

(15, 'SOL-OA-03', 'Lente de sol Oakley active',
 'Ajuste perfecto para actividades al aire libre', 88990.00, 16, '2025-11-18', '/images/PRODUCTOS/SOL/s_8.webp', 2, 2),

-- LENTES DE CONTACTO (Categoria 3)
(16, 'CON-GEN-01', 'Lentes de contacto diarios',
 'Caja con 30 unidades para uso diario', 25990.00, 40, '2025-11-18', '/images/PRODUCTOS/CONTACTO/c_1.webp', 3, 4),

(17, 'CON-GEN-02', 'Lentes de contacto mensuales',
 'Pack de 6 lentes mensuales', 35990.00, 35, '2025-11-18', '/images/PRODUCTOS/CONTACTO/c_2.webp', 3, 4),

(18, 'CON-GEN-03', 'Lentes de contacto de color azul',
 'Lentes cosmeticos color azul natural', 29990.00, 25, '2025-11-18', '/images/PRODUCTOS/CONTACTO/c_3.webp', 3, 4),

(19, 'CON-GEN-04', 'Lentes de contacto de color verde',
 'Lentes cosmeticos color verde esmeralda', 29990.00, 25, '2025-11-18', '/images/PRODUCTOS/CONTACTO/c_4.webp', 3, 4),

(20, 'CON-GEN-05', 'Lentes de contacto toricos',
 'Para correccion de astigmatismo', 45990.00, 20, '2025-11-18', '/images/PRODUCTOS/CONTACTO/c_5.webp', 3, 4),

(21, 'CON-GEN-06', 'Lentes de contacto multifocales',
 'Para vista cansada y presbicia', 55990.00, 15, '2025-11-18', '/images/PRODUCTOS/CONTACTO/c_6.webp', 3, 4),

-- ACCESORIOS (Categoria 4)
(22, 'ACC-GEN-01', 'Liquido limpieza 250ml',
 'Solucion profesional para limpieza de lentes', 5990.00, 50, '2025-11-18', '/images/PRODUCTOS/ACCESORIOS/a_1.webp', 4, 4),

(23, 'ACC-GEN-02', 'Estuche rigido para lentes',
 'Estuche protector con cierre seguro', 7990.00, 60, '2025-11-18', '/images/PRODUCTOS/ACCESORIOS/a_2.webp', 4, 4),

(24, 'ACC-GEN-03', 'Pano de microfibra premium',
 'Pack de 3 panos ultra suaves', 4990.00, 80, '2025-11-18', '/images/PRODUCTOS/ACCESORIOS/a_3.webp', 4, 4),

(25, 'ACC-GEN-04', 'Cordon para lentes deportivo',
 'Cordon ajustable para actividades deportivas', 3990.00, 45, '2025-11-18', '/images/PRODUCTOS/ACCESORIOS/a_4.webp', 4, 4),

(26, 'ACC-GEN-05', 'Kit de reparacion para lentes',
 'Incluye tornillos y destornillador miniatura', 8990.00, 30, '2025-11-18', '/images/PRODUCTOS/ACCESORIOS/a_5.webp', 4, 4),

(27, 'ACC-GEN-06', 'Spray antivaho profesional',
 'Previene empanamiento en lentes', 6990.00, 40, '2025-11-18', '/images/PRODUCTOS/ACCESORIOS/a_6.webp', 4, 4);

-- ============================
-- USUARIOS
-- Nota: La contrasena para ambos es '123456'
-- ============================

INSERT INTO usuario (nombre, apellido, rut, email, password_hash, rol, fecha_registro, activo) VALUES 
('Administrador', 'Visso', '11.111.111-1', 'admin@visso.cl', '$2a$10$y3RdScRbLPiYqtCRtXCDzedIhizQ7qvXGRLO75xcIHDskqK9Gry1K', 'admin', '2024-11-20', 1);

INSERT INTO usuario (nombre, apellido, rut, email, password_hash, rol, fecha_registro, activo) VALUES 
('Sofi', 'Munoz', '21.970.360-0', 'sofi@duocuc.cl', '$2a$10$y3RdScRbLPiYqtCRtXCDzedIhizQ7qvXGRLO75xcIHDskqK9Gry1K', 'usuario', '2024-11-20', 1);