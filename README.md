# ProyectoAndroid

**Una aplicación Android diseñada para digitalizar y gestionar el ecosistema de los juegos de mesa de rol (RPG) en tiempo real.** El sistema elimina la necesidad de usar papel y lápiz, centralizando hojas de personaje, inventarios y estadísticas en una interfaz interactiva y dinámica.

## 🚀 Factores Clave

* **Redacción digital:** Reemplaza por completo el uso de escritura física por un sistema digital ágil.
* **Sincronización en tiempo real:** Permite la conexión entre usuarios para compartir datos de la partida instantáneamente.
* **Personalización avanzada:** Ofrece variadas opciones para que cada usuario adapte la interfaz a sus necesidades.

## 📋 Requerimientos del Sistema

### Gestión de Usuarios y Conectividad
* **Autenticación:** El usuario contará con una cuenta personal para iniciar sesión y acceder a sus archivos asociados.
* **Persistencia en la nube:** La base de datos estará conectada a la nube para garantizar el acceso a la información en cualquier momento y lugar.
* **Arquitectura Cliente-Servidor:** El sistema guardará todos los documentos y cambios de cada usuario directamente en la base de datos centralizada.

### Dinámica de Partida (Roles)
* **Creación de Sesiones (DM):** El usuario podrá crear sesiones de rol en condición de *Game Master* / *Dungeon Master* (DM) para que otros se unan.
* **Unirse a Sesiones (Jugador):** El usuario podrá unirse a sesiones activas creadas por otros usuarios en condición de *Jugador*.

### Módulo del Jugador
* **Hoja de Personaje:** El jugador poseerá una "Hoja de Vida" digital con información del personaje, estadísticas, inventario y habilidades.
* **Bloc de Notas:** Sección de notas integrada para registrar eventos, pistas o detalles importantes durante la sesión.

### Módulo del Dungeon Master (DM)
* **Gestor de Archivos:** Menú dedicado para acceder a documentos cargados (formatos compatibles: PDF, TXT, DOCX) y generar notas de campaña complejas.
* **Gestor de Combate (Enemigos):** Sección para generar fichas de enemigos (recuadros dinámicos con puntos de vida y estadísticas básicas) de forma ilimitada y modificables en tiempo real durante los encuentros.

## 🛠️ Stack Tecnológico

* **Lenguaje de Programación:** Kotlin
* **Manejo de UI:** Jetpack Compose (Declarativa) / XML Views (Tradicional)
* **Base de Datos Local:** Room Database (para persistencia y soporte offline)
* **Backend & Nube (Firebase):**
  * *Firebase Authentication:* Registro e inicio de sesión seguro.
  * *Cloud Firestore / Realtime Database:* Sincronización de datos entre DM y jugadores en tiempo real.
  * *Firebase Cloud Storage:* Almacenamiento de archivos adjuntos (PDFs, imágenes de personajes, etc.).
* **Concurrencia:** Kotlin Coroutines & Flow (para flujos de datos reactivos).
* **Inyección de Dependencias:** Hilt / Koin

## 📐 Arquitectura de Software

El proyecto está desarrollado bajo el patrón de arquitectura **MVVM (Model-View-ViewModel)** siguiendo los principios de **Clean Architecture** para garantizar un código escalable, mantenible y testeable:

* **Presentación (UI):** Views/Composables y ViewModels que reaccionan al estado de la pantalla.
* **Dominio:** Casos de uso (*Use Cases*) que contienen la lógica de negocio pura del juego de rol (ej. calcular daño, subir de nivel, unirse a sesión).
* **Datos:** Repositorios encargados de decidir si la información se obtiene del caché local (Room) o del servidor remoto (Firebase).

## ⚙️ Requisitos Técnicos

* **Mínimo SDK:** API 26 (Android 8.0 Oreo) - Para asegurar compatibilidad con la mayoría de dispositivos modernos.
* **SDK Objetivo:** API 34 (Android 14) o superior.
* **Entorno de Desarrollo:** Android Studio Jellyfish (o versión más reciente).
* **Versión de Gradle:** 8.0+