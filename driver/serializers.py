from rest_framework import serializers
from rest_framework.fields import SerializerMethodField

from .models import Driver, Zone, RideRequest

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
                    'isActive',
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
                    'isActive',
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
                    'isActive',
                    'car_seats',
                    'smoking_allowed',
                  ]

class DriverUpdateSerializer(serializers.ModelSerializer):

    class Meta:
        model = Driver
        fields = [  'isBusy',
                    'car_seats',
                    'smoking_allowed',
                  ]

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

class DriverChangeActiveSerializer(serializers.ModelSerializer):
    class Meta:
        model = Driver
        fields = [
            'isActive',
        ]



class RideRequestSerializer(serializers.ModelSerializer):
    passenger = SerializerMethodField()
    class Meta:
        model = RideRequest
        fields = [
            'passenger',
            'driver',
            'start',
            'end',
            'pickuploc',
            'seats',
            'over',
            'accepted',
        ]

    def get_passenger(self, obj):
        return obj.passenger.name


class RideRequestCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = RideRequest
        fields = [
            'driver',
            'start',
            'end',
            'pickuploc',
            'seats',
        ]

class RideRequestUpdateSerializer(serializers.ModelSerializer):
    accepted = SerializerMethodField()
    class Meta:
        model = RideRequest
        fields = [
            'driver',
            'start',
            'end',
            'pickuploc',
            'seats',
            'accepted',
        ]

    def get_accepted(self, obj):
        object = obj.accepted
        obj.accepted = False
        obj.save()
        return object



class RideRequestAddDriverSerializer(serializers.ModelSerializer):
    class Meta:
        model = RideRequest
        fields = [
            'driver',
            'over',
        ]

class RideRequestDriverFirstSerializer(serializers.ModelSerializer):
    class Meta:
        model = RideRequest
        fields = [
            'driver',
            'passenger',
            'seats',
        ]

class RideRequestDetailSerializer(serializers.ModelSerializer):
    passenger = SerializerMethodField()
    class Meta:
        model = RideRequest
        fields = [
            'passenger',
            'driver',
            'start',
            'end',
            'pickuploc',
            'seats',
            'over',
            'accepted',
        ]

    def get_passenger(self, obj):
        return obj.passenger.name

class AcceptRequestUpdateSerializer(serializers.ModelSerializer):
    accepted = SerializerMethodField()
    class Meta:
        model = RideRequest
        fields = [
            'accepted',
        ]

    def get_accepted(self, obj):
        object = obj.accepted
        obj.accepted = True
        obj.save()
        return object
