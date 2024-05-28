from django.contrib import admin
from django.urls import path

from saborexpressapp import endpoints


urlpatterns = [
    path('admin/', admin.site.urls),
    path('user', endpoints.user),
    path('user/session', endpoints.sessions),
    path('search_recipes', endpoints.search_recipes)
]
