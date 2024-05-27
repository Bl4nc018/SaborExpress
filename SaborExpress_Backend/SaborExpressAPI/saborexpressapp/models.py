from django.db import models


class User(models.Model):
    image = models.CharField(max_length=100)
    username = models.CharField(unique=True, max_length=100)
    foodtype = models.CharField(max_length=100)
    password = models.CharField(max_length=100)

    def __str__(self):
        return self.name

    def to_json(self):
        return {
            "image": self.image,
            "username": self.username,
            "foodtype": self.foodtype,
        }


class UserSession(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    token = models.CharField(unique=True, max_length=45)
