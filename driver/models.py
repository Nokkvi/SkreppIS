import json
from django.contrib.auth.models import User
from django.db.models.signals import post_save
from django.dispatch import receiver
from django.template.defaultfilters import slugify

from django.db import models

from passenger.models import Passenger





class DriverManager(models.Manager):
    def create_driver(self, user):
        name = user.get_username()
        phone_number = Passenger.objects.get(pk=user.pk).phone_number
        description = Passenger.objects.get(pk=user.pk).description
        image = Passenger.object.get(pk=user.pk).image
        driver = self.create(user=user, name=name, phone_number=phone_number, description=description, image=image)
        return driver

class Driver(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE, null=True)
    name = models.CharField(max_length=50, blank=True)
    isActive = models.BooleanField(default=False)
    phone_number = models.CharField(max_length=16, blank=True)
    description = models.CharField(max_length=200, blank=True)
    image = models.CharField(max_length=200, blank=True)
    car_seats = models.IntegerField(null=True)
    smoking_allowed = models.BooleanField(default=False)

    objects = DriverManager

    def __iter__(self):
        return [
            self.user_id
        ]

    def __str__(self):
        return self.name

    def set_requests(self, request_list):
        self.requests = json.dumps(request_list)

    def get_requests(self):
        return json.loads(self.requests)

    def save(self, **kwargs):
        self.slug = slugify(self.name)
        super(Driver, self).save()


class Zone(models.Model):
    name = models.CharField(max_length=100, blank=True, null=True)
    driver = models.ForeignKey(Driver, null=True, blank=True)

    def get_name(self):
        return self.name

class DriverFare(models.Model):
    passenger = models.ForeignKey(Passenger)
    driver = models.ForeignKey(Driver, on_delete=models.CASCADE)

class Fare(models.Model):
    passengerName = models.ForeignKey(Passenger, null=True, blank=True)
    driver = models.ForeignKey(Driver, null=True, blank=True)
    pickup = models.CharField(max_length=100, blank=True, null=True)
    dropoff = models.CharField(max_length=100, null=True, blank=True)
    seats = models.IntegerField(null=True)
    over = models.BooleanField(default=False)

class RideRequest(models.Model):
    passenger = models.ForeignKey(Passenger, null=True, blank=True)
    assigneddriver = models.ForeignKey(Driver, null=True, blank=True)
    start = models.CharField(max_length=100, blank=True, null=True)
    end = models.CharField(max_length=100, null=True, blank=True)
    pickuploc = models.CharField(max_length=100, null=True, blank=True)
    seats = models.IntegerField(null=True)
    over = models.BooleanField(default=False)

