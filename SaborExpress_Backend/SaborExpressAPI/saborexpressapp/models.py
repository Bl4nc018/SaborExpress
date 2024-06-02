from django.db import models


# Modelo para representar un usuario en el sistema
class User(models.Model):
    image = models.CharField(max_length=500)  # URL de la imagen del usuario
    username = models.CharField(unique=True, max_length=100)  # Nombre de usuario, debe ser único
    foodtype = models.CharField(max_length=100)  # Tipo de comida favorita del usuario
    password = models.CharField(max_length=100)  # Contraseña cifrada del usuario

    def __str__(self):
        return self.username  # Representación del modelo como cadena, se muestra el nombre de usuario

    def to_json(self):
        # Convierte los detalles del usuario a un formato JSON
        return {
            "image": self.image,
            "username": self.username,
            "foodtype": self.foodtype,
        }


# Modelo para representar una sesión de usuario
class UserSession(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)  # Relación con el usuario
    token = models.CharField(unique=True, max_length=45)  # Token único de sesión para el usuario


# Modelo para representar una receta
class Recipe(models.Model):
    recipe_name = models.CharField(max_length=255)  # Nombre de la receta
    description = models.CharField(max_length=300)  # Descripción breve de la receta
    food_type = models.CharField(max_length=100)  # Tipo de comida de la receta
    ingredients = models.TextField()  # Lista de ingredientes de la receta
    steps = models.TextField()  # Pasos para preparar la receta
    image_url = models.CharField(max_length=500)  # URL de la imagen de la receta
    author = models.ForeignKey(User, on_delete=models.CASCADE, related_name='recipes')  # Autor de la receta

    def __str__(self):
        return self.recipe_name  # Representación del modelo como cadena, se muestra el nombre de la receta
