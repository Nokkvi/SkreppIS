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

class Request(models.Model):
    passenger = models.ForeignKey(Passenger, on_delete=models.CASCADE)
    driver = models.ManyToManyField(Driver)
    inTown = models.BooleanField(default=True)
    destination = models.ForeignKey(Zone)
    arrivalTime = models.DateTimeField()
    status = models.CharField(max_length=16, default="pending")

    def __str__(self):
        return "Frá " + self.passenger.name

