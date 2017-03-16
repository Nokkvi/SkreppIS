from django.db import models
from django.contrib.auth.models import User
from django.db.models.signals import post_save
from django.dispatch import receiver

class Zone(models.Model):
    name = models.CharField(max_length=100, blank=True, null=True)

    def __str__(self):
        return self.name

class PassengerManager(models.Manager):
    def create_passenger(self, phone_number, user):
        name = user.get_username()
        passenger = self.create(user=user, name=name, phone_number=phone_number)
        return passenger

class Passenger(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE, null=True)
    name = models.CharField(max_length=50, blank=True)
    phone_number = models.CharField(max_length=16, blank=True)
    zones = models.ManyToManyField(Zone, null=True)

    objects = PassengerManager()

    def __str__(self):
        return self.name
