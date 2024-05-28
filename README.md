# 🍽️ SaborExpress

**SaborExpress** es una aplicación móvil desarrollada como proyecto de TFG de un grado superior en DAM. Con una interfaz intuitiva, serás capaz de descubrir, guardar y compartir sencillas recetas culinarias. ¡Descubre el placer de cocinar con SaborExpress!

|[Docs](https://example.com/docs)|

## 🌟 Características

- 🔍 Descubre nuevas recetas sencillas que cualquier principiante es capaz de continuar.
- 💾 Guarda tus recetas favoritas para volver a hacerlas más tarde.
- 📤 Comparte tus propias recetas con tus amigos.
- 📱 Interfaz de usuario intuitiva y fácil de usar, hasta los más mayores pueden usarla.

## 🛠️ Tecnologías Utilizadas

- **Frontend**: Java
- **Backend**: Python
- **Base de Datos**: SQLite

## 🚀 Instalación

### Prerrequisitos

- Java 8 o superior
- Python 3.6 o superior
- [Android Studio](https://developer.android.com/studio)

### Pasos para la instalación

1. Clonar el repositorio:
    ```sh
    git clone https://github.com/Bl4nc018/SaborExpress.git
    ```
2. Configurar el backend:
    ```sh
    cd SaborExpress_Backend
    python manage.py migrate
    python manage.py runserver
    ```
3. Configurar el frontend:
    - Abrir Android Studio.
    - Importar el proyecto ubicado en `SaborExpress_App`.
    - Ejecutar la aplicación en un emulador o dispositivo físico.

## 📚 Uso

1. Inicia el servidor backend siguiendo los pasos de instalación.
2. Abre la aplicación móvil en tu dispositivo o emulador.
3. Explora, guarda y comparte recetas fácilmente.

## 🔄 Actualización

Para actualizar a una nueva versión, puedes seguir estos pasos:

1. **Si has modificado la configuración:**
    - Revisa los nuevos commits y actualiza manualmente según sea necesario.

2. **Si no has realizado modificaciones significativas:**
    - Elimina todos los archivos antiguos.
    - Sigue los pasos de instalación nuevamente.
    - Restaura tus configuraciones personales si es necesario.

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.

## 📧 Contacto

Desarrollado por Bl4nc018 - [bl4nc018@example.com](mailto:bl4nc018@example.com)




Quick start
===========

Please read our `Command Reference <https://example.com/docs/command-reference>`_ for a complete list.

A common CLI workflow includes:

| Task                              | Terminal                                                                                           |
|-----------------------------------|----------------------------------------------------------------------------------------------------|
| Track data                        | `$ git add train.py params.yaml` <br> `$ dvc add images/`                                          |
| Connect code and data             | `$ dvc stage add -n featurize -d images/ -o features/ python featurize.py` <br> `$ dvc stage add -n train -d features/ -d train.py -o model.p -M metrics.json python train.py` |
| Make changes and experiment       | `$ dvc exp run -n exp-baseline` <br> `$ vi train.py` <br> `$ dvc exp run -n exp-code-change`       |
| Compare and select experiments    | `$ dvc exp show` <br> `$ dvc exp apply exp-baseline`                                               |
| Share code                        | `$ git add .` <br> `$ git commit -m 'The baseline model'` <br> `$ git push`                        |
| Share data and ML models          | `$ dvc remote add myremote -d s3://mybucket/image_cnn` <br> `$ dvc push`                           |


