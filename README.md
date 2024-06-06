![Logo](https://github.com/Bl4nc018/SaborExpress/blob/main/Logo.png)

# 🍽️ SaborExpress

**SaborExpress** es una aplicación móvil desarrollada como proyecto de TFG de un grado superior en DAM. Con una interfaz intuitiva, serás capaz de descubrir, guardar y compartir sencillas recetas culinarias. ¡Descubre el placer de cocinar con SaborExpress!

<p align="left">
   <img src="https://img.shields.io/badge/Build-Passing-brightgreen">
   <img src="https://img.shields.io/badge/Java-11-orange">
   <img src="https://img.shields.io/badge/Python-3.8-blue">
   <img src="https://img.shields.io/badge/Security-bcrypt-blue">
   <img src="https://img.shields.io/badge/Platform-Android-green">
   <img src="https://img.shields.io/badge/Database-SQLite-blue">
   <img src="https://img.shields.io/badge/Backend-Django-yellowgreen">
   <img src="https://img.shields.io/badge/Frontend-Android%20Studio-red">
   <img src="https://img.shields.io/badge/Maintained-yes-brightgreen">
   <img src="https://img.shields.io/badge/Version-1.0-blue">
</p>

<br/>

<h2 align="left">🌟 Características</h2>

- 🔍 Descubre nuevas recetas sencillas que cualquier principiante es capaz de continuar.
- 💾 Guarda tus recetas favoritas para volver a hacerlas más tarde.
- 📤 Comparte tus propias recetas con tus amigos.
- 📱 Interfaz de usuario intuitiva y fácil de usar, hasta los más mayores pueden usarla.

<br/>

<h2 align="left">🔧 Detalles Técnicos </h2>

- **Lenguaje de Programación**: Java para el frontend, Python para el backend.
- **Frameworks**: Android Studio, Django.
- **Dependencias**: 
  - Backend: Django, Django REST Framework, bcrypt
  - Frontend: Android SDK

<br/>

<h2 align="left">🚀 Instalación</h2>

### Prerrequisitos

- Java 8 o superior
- Python 3.6 o superior
- [Android Studio](https://developer.android.com/studio)
- Git

### Pasos para la instalación

1. **Clonar el repositorio**:
    ```sh
    git clone https://github.com/Bl4nc018/SaborExpress.git
    ```

2. **Configurar el backend**:
    - Navegar al directorio del backend:
      ```sh
      cd SaborExpress
      ```
    - Crear y activar un entorno virtual (Solo si no tienes las dependencias ya instaladas):
      ```sh
      python -m venv venv
      ```
        (Procura no tener restringido el uso de scripts en tu sistema)
      - Si estás empleando Windows:
         ```sh
         .venv\Scripts\activate
         ```
      - Si estás empleando Linux:
         ```sh
         source venv/bin/activate
         ```
    - Instalar las dependencias necesarias:
      ```sh
      pip install -r requirements.txt
      ```
    - Realizar las migraciones de la base de datos:
      ```sh
      cd SaborExpress_Backend\SaborExpressAPI
      python manage.py migrate
      ```
    - Iniciar el servidor de desarrollo:
      ```sh
      python manage.py runserver
      ```

   ## Eliminar el entorno virtual
   
   Si deseas eliminar el entorno virtual, sigue estos pasos:
   
   1. **Desactiva el entorno virtual (si está activo):**
       ```powershell
       deactivate
       ```
   
   2. **Elimina el directorio del entorno virtual:**
       ```powershell
       Remove-Item -Recurse -Force .\venv
       ```
       
   Procura asegurarte de que estás en el mismo lugar donde existe la carpeta venv.
   Este comando eliminará el entorno virtual y liberará el espacio en disco utilizado por el mismo.
      

4. **Configurar el frontend**:
    - Abrir Android Studio.
    - Importar el proyecto ubicado en `SaborExpress/SaborExpress_App`.
    - Configurar un emulador o conectar un dispositivo físico:
      - Si estás usando un emulador, asegúrate de que esté configurado y funcionando en Android Studio.
      - Si estás usando un dispositivo físico, habilita el modo de desarrollador y la depuración USB o inalámbrica, según lo que prefieras.
    - Ejecutar la aplicación:
      - Haz clic en el botón "Run" en Android Studio para compilar y ejecutar la aplicación en el emulador o dispositivo conectado.
     
<br/>

<h2 align="left">📚 Uso</h2>

1. Inicia el servidor backend siguiendo los pasos de instalación.
2. Abre la aplicación móvil en tu dispositivo o emulador.
3. Explora, guarda y comparte recetas fácilmente.

<br/>

<h2 align="left">🔄 Actualización</h2>

Para actualizar a una nueva versión, puedes seguir estos pasos:

1. **Si has modificado la configuración:**
    - Revisa los nuevos commits y actualiza manualmente según sea necesario.

2. **Si no has realizado modificaciones significativas:**
    - Elimina todos los archivos antiguos.
    - Sigue los pasos de instalación nuevamente.
    - Restaura tus configuraciones personales si es necesario.

<br/>

<h2 align="left">🔒 Licencia</h2>

Este proyecto está bajo la Licencia Apache 2.0. Consulta el archivo [LICENSE](https://github.com/Bl4nc018/SaborExpress/blob/main/LICENSE) para más detalles.

<br/>

<h2 align="left">:hammer: Desarrolladora</h2>

<p align="left">
   <a href="https://github.com/Bl4nc018">
      <img src="https://avatars.githubusercontent.com/u/92156488?s=400&u=1302f75511bad4df69803bf7b66443a1a8364b60&v=4" width=115><br>
      <sub>Ania</sub>
   </a>
</p>
