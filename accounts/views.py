from django.shortcuts import render
from django.contrib.auth import get_user_model

from .serializers import (
    UserCreateSerializer
)

from rest_framework.generics import (
    CreateAPIView,
    DestroyAPIView,
    ListAPIView,
    UpdateAPIView,
    RetrieveAPIView,
    RetrieveUpdateAPIView
    )

User = get_user_model()

# Create your views here.
class UserCreateApiView(CreateAPIView):
    serializer_class = UserCreateSerializer
    queryset = User.objects.all()