from rest_framework import serializers
from rest_framework.fields import SerializerMethodField

from .models import Driver, Request, Zone

from ratings.serializers import RatingSerializer
from ratings.models import Rating, RatingManager

class DriverSerializer(serializers.ModelSerializer):
    zones = SerializerMethodField()
    rating = SerializerMethodField()
    class Meta:
        model = Driver
        fields = [  'name',
                    'isActive',
                    'phone_number',
                    'zones',
                    'rating',
                    'car_seats',
                  ]

    def get_zones(self, obj):
        c_qs = Zone.objects.filter(driver__name=obj)
        zones = ZoneSerializer(c_qs, many=True).data
        return zones

    def get_rating(self, obj):
        c_qs = Rating.objects.filter(driver__name=obj)
        ratings = RatingSerializer(c_qs, many=True).data
        return ratings

class DriverDetailSerializer(serializers.ModelSerializer):
    rating = SerializerMethodField()
    zones = SerializerMethodField()
    class Meta:
        model = Driver
        fields = [  'name',
                    'phone_number',
                    'description',
                    'zones',
                    'rating',
                    'car_seats',
                  ]

    def get_zones(self, obj):
        c_qs = Zone.objects.filter(driver__name=obj)
        zones = ZoneSerializer(c_qs, many=True).data
        return zones

    def get_rating(self, obj):
        c_qs = Rating.objects.filter(driver__name=obj)
        ratings = RatingSerializer(c_qs, many=True).data
        return ratings



class DriverCreateSerializer(serializers.ModelSerializer):

    class Meta:
        model = Driver
        fields = [  #'id',
                    #'passenger',
                    #''
                    'car_seats',
                  ]

class DriverToggleActiveSerializer(serializers.ModelSerializer):
    isActive = SerializerMethodField()
    class Meta:
        model = Driver
        fields = [
            'isActive',
        ]

    def get_isActive(self, obj):
        object = obj.isActive
        obj.isActive = not object
        obj.save()
        return object

class DriverUpdateSerializer(serializers.ModelSerializer):

    class Meta:
        model = Driver
        fields = [  'isBusy',
                    'car_seats',
                    'smoking_allowed',
                  ]

class RequestSerializer(serializers.ModelSerializer):

    class Meta:
        model = Request
        fields = '__all__'

class ZoneSerializer(serializers.ModelSerializer):
    driver = SerializerMethodField()
    class Meta:
        model = Zone
        fields = [
            'name',
            'driver',
        ]

    def get_driver(self, obj):
        return obj.driver.name


class ZoneCreateSerializer(serializers.ModelSerializer):
    driver = SerializerMethodField()
    class Meta:
        model = Zone
        fields = [
            'name',
            'driver',
        ]

    def get_driver(self, obj):
        return obj.driver.name

class ZoneDetailSerializer(serializers.ModelSerializer):
    driver = SerializerMethodField()
    class Meta:
        model = Zone
        fields = [  'name',
                    'driver',
                  ]

    def get_driver(self, obj):
        return obj.driver.name