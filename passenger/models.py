from django.db import models
from django.contrib.auth.models import User
from django.db.models.signals import post_save
from django.dispatch import receiver


class PassengerManager(models.Manager):
    def create_passenger(self, phone_number, user):
        name = user.get_username()
        passenger = self.create(user=user, name=name, phone_number=phone_number)
        return passenger

class Passenger(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE, null=True)
    name = models.CharField(max_length=50, blank=True)
    phone_number = models.CharField(max_length=16, blank=True)
    description = models.CharField(max_length=200, blank=True)
    image = models.CharField(max_length=200, blank=True)

    objects = PassengerManager()

    def __str__(self):
        return self.name

class Zone(models.Model):
    name = models.CharField(max_length=100, blank=True, null=True)
    passenger = models.ForeignKey(Passenger, null=True, blank=True)

    def __str__(self):
        return self.name

class RideRequest(models.Model):
    passenger = models.ForeignKey(Passenger, null=True, blank=True)
    start = models.CharField(max_length=100, blank=True, null=True)
    end = models.CharField(max_length=100, null=True, blank=True)

