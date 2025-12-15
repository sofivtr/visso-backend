-- ============================
-- CATEGORIAS (Sin tildes)
-- ============================
INSERT IGNORE INTO categoria (id_categoria, nombre) VALUES
(1, 'Opticos'),
(2, 'Lentes de sol'),
(3, 'Lentes de contacto'),
(4, 'Accesorios');

-- ============================
-- MARCAS
-- ============================
INSERT IGNORE INTO marca (id_marca, nombre, imagen) VALUES
(1, 'Ray-Ban', '/images/MARCAS/ray-ban-logo_2024_1.png'),
(2, 'Dolce & Gabbana', '/images/MARCAS/dolce.png'),
(3, 'Giorgio Armani', '/images/MARCAS/giorgio.png'),
(4, 'Persol', '/images/MARCAS/persol_1.png'),
(5, 'Platini', '/images/MARCAS/platini_1.png'),
(6, 'Polo Ralph Lauren', '/images/MARCAS/polo.png'),
(7, 'Ralph Lauren', '/images/MARCAS/ralph_lauren_1.png'),
(8, 'Sferoflex', '/images/MARCAS/steroflex.png'),
(9, 'Vogue Eyewear', '/images/MARCAS/vogue_eyewear.png'),
(10, 'Generico', '/images/MARCAS/generico.webp');

-- ============================
-- PRODUCTOS
-- ============================
INSERT IGNORE INTO producto (
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
 'Marco metalico elegante con diseno atemporal', 79990.00, 10, '2025-11-18', '/images/PRODUCTOS/OPTICOS/0_4.webp', 1, 1),

(2, 'OPT-VO-01', 'Lente optico Vogue Eyewear',
 'Marco de acetato con estilo femenino y moderno', 65990.00, 8, '2025-11-18', '/images/PRODUCTOS/OPTICOS/o_1.webp', 1, 9),

(3, 'OPT-SF-01', 'Lente optico Sferoflex flexible',
 'Marco ligero y flexible ideal para uso diario', 58990.00, 12, '2025-11-18', '/images/PRODUCTOS/OPTICOS/o_2.webp', 1, 8),

(4, 'OPT-GA-01', 'Lente optico Giorgio Armani premium',
 'Diseno de lujo italiano con detalles exclusivos', 149990.00, 5, '2025-11-18', '/images/PRODUCTOS/OPTICOS/o_3.webp', 1, 3),

(5, 'OPT-PL-01', 'Lente optico Platini rectangular',
 'Marco rectangular versatil para todo tipo de rostro', 52990.00, 15, '2025-11-18', '/images/PRODUCTOS/OPTICOS/o_5.webp', 1, 5),

(6, 'OPT-PE-01', 'Lente optico Persol redondo',
 'Estilo vintage italiano con marco redondo', 129990.00, 7, '2025-11-18', '/images/PRODUCTOS/OPTICOS/o_6.webp', 1, 4),

(7, 'OPT-RL-01', 'Lente optico Ralph Lauren clasico',
 'Diseno elegante con acabado sofisticado', 89990.00, 10, '2025-11-18', '/images/PRODUCTOS/OPTICOS/o_7.webp', 1, 7),

-- LENTES DE SOL (Categoria 2)
(8, 'SOL-RB-01', 'Lente de sol Ray-Ban Wayfarer',
 'Modelo iconico con proteccion UV400 y estilo atemporal', 89990.00, 20, '2025-11-18', '/images/PRODUCTOS/SOL/s_1.webp', 2, 1),

(9, 'SOL-DG-01', 'Lente de sol Dolce & Gabbana elegante',
 'Lente con cristales polarizados y diseno italiano', 159990.00, 15, '2025-11-18', '/images/PRODUCTOS/SOL/s_2.webp', 2, 2),

(10, 'SOL-GA-01', 'Lente de sol Giorgio Armani fashion',
 'Diseno exclusivo de alta costura con proteccion total', 179990.00, 8, '2025-11-18', '/images/PRODUCTOS/SOL/s_3.webp', 2, 3),

(11, 'SOL-PE-01', 'Lente de sol Persol aviador',
 'Clasico italiano con lentes cristalinos y montura premium', 139990.00, 18, '2025-11-18', '/images/PRODUCTOS/SOL/s_4.webp', 2, 4),

(12, 'SOL-PO-01', 'Lente de sol Polo Ralph Lauren deportivo',
 'Estilo deportivo elegante con tecnologia UV', 99990.00, 12, '2025-11-18', '/images/PRODUCTOS/SOL/s_5.webp', 2, 6),

(13, 'SOL-VO-01', 'Lente de sol Vogue Eyewear oversized',
 'Montura grande estilo glamoroso y femenino', 72990.00, 6, '2025-11-18', '/images/PRODUCTOS/SOL/s_6.webp', 2, 9),

(14, 'SOL-RB-02', 'Lente de sol Ray-Ban clubmaster',
 'Estilo retro vintage con marco combinado', 92990.00, 14, '2025-11-18', '/images/PRODUCTOS/SOL/s_7.webp', 2, 1),

(15, 'SOL-PL-01', 'Lente de sol Platini casual',
 'Diseno casual y comodo para el dia a dia', 54990.00, 16, '2025-11-18', '/images/PRODUCTOS/SOL/s_8.webp', 2, 5),

-- LENTES DE CONTACTO (Categoria 3)
(16, 'CON-GEN-01', 'Lentes de contacto diarios',
 'Caja con 30 unidades para uso diario comodo e higienico', 25990.00, 40, '2025-11-18', '/images/PRODUCTOS/CONTACTO/c_1.webp', 3, 10),

(17, 'CON-GEN-02', 'Lentes de contacto mensuales',
 'Pack de 6 lentes mensuales de alta calidad', 35990.00, 35, '2025-11-18', '/images/PRODUCTOS/CONTACTO/c_2.webp', 3, 10),

(18, 'CON-GEN-03', 'Lentes de contacto de color azul',
 'Lentes cosmeticos color azul natural con efecto realista', 29990.00, 25, '2025-11-18', '/images/PRODUCTOS/CONTACTO/c_3.webp', 3, 10),

(19, 'CON-GEN-04', 'Lentes de contacto de color verde',
 'Lentes cosmeticos color verde esmeralda intenso', 29990.00, 25, '2025-11-18', '/images/PRODUCTOS/CONTACTO/c_4.webp', 3, 10),

(20, 'CON-GEN-05', 'Lentes de contacto toricos',
 'Lentes especializados para correccion de astigmatismo', 45990.00, 20, '2025-11-18', '/images/PRODUCTOS/CONTACTO/c_5.webp', 3, 10),

(21, 'CON-GEN-06', 'Lentes de contacto multifocales',
 'Solucion efectiva para vista cansada y presbicia', 55990.00, 15, '2025-11-18', '/images/PRODUCTOS/CONTACTO/c_6.webp', 3, 10),

-- ACCESORIOS (Categoria 4)
(22, 'ACC-GEN-01', 'Liquido limpieza 250ml',
 'Solucion profesional para limpieza de lentes opticos y de sol', 5990.00, 50, '2025-11-18', '/images/PRODUCTOS/ACCESORIOS/a_1.webp', 4, 10),

(23, 'ACC-GEN-02', 'Estuche rigido para lentes',
 'Estuche protector con cierre seguro y forro interior', 7990.00, 60, '2025-11-18', '/images/PRODUCTOS/ACCESORIOS/a_2.webp', 4, 10),

(24, 'ACC-GEN-03', 'Pano de microfibra premium',
 'Pack de 3 panos ultra suaves que no rayan', 4990.00, 80, '2025-11-18', '/images/PRODUCTOS/ACCESORIOS/a_3.webp', 4, 10),

(25, 'ACC-GEN-04', 'Cordon para lentes deportivo',
 'Cordon ajustable y resistente para actividades deportivas', 3990.00, 45, '2025-11-18', '/images/PRODUCTOS/ACCESORIOS/a_4.webp', 4, 10),

(26, 'ACC-GEN-05', 'Kit de reparacion para lentes',
 'Kit completo con tornillos, destornillador y almohadillas', 8990.00, 30, '2025-11-18', '/images/PRODUCTOS/ACCESORIOS/a_5.webp', 4, 10),

(27, 'ACC-GEN-06', 'Spray antivaho profesional',
 'Formula especial que previene el empanamiento de lentes', 6990.00, 40, '2025-11-18', '/images/PRODUCTOS/ACCESORIOS/a_6.webp', 4, 10);

-- ============================
-- USUARIOS
-- Nota: La contrasena para todos es '123456'
-- ============================

INSERT IGNORE INTO usuario (nombre, apellido, rut, email, password_hash, rol, fecha_registro, activo) VALUES 
('Administrador', 'Visso', '22.002.404-0', 'admin@visso.cl', '$2a$10$y3RdScRbLPiYqtCRtXCDzedIhizQ7qvXGRLO75xcIHDskqK9Gry1K', 'admin', '2024-11-20', 1);

INSERT IGNORE INTO usuario (nombre, apellido, rut, email, password_hash, rol, fecha_registro, activo) VALUES 
('Sofi', 'Munoz', '21.970.360-0', 'sofi@duocuc.cl', '$2a$10$y3RdScRbLPiYqtCRtXCDzedIhizQ7qvXGRLO75xcIHDskqK9Gry1K', 'usuario', '2024-11-20', 1);

INSERT IGNORE INTO usuario (nombre, apellido, rut, email, password_hash, rol, fecha_registro, activo) VALUES 
('Vendedor', 'Visso', '15.485.101-1', 'vendedor@visso.cl', '$2a$10$y3RdScRbLPiYqtCRtXCDzedIhizQ7qvXGRLO75xcIHDskqK9Gry1K', 'vendedor', '2024-11-20', 1);
