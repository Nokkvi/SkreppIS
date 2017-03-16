from rest_framework import serializers
from rest_framework.fields import SerializerMethodField

from .models import Driver, Request

from ratings.serializers import RatingSerializer
from ratings.models import Rating, RatingManager

class DriverSerializer(serializers.ModelSerializer):

    class Meta:
        model = Driver
        fields = [  'id',
                    'user',
                    'name',
                    'phone_number',
                    'zones',
                  ]

class DriverDetailSerializer(serializers.ModelSerializer):
    rating = SerializerMethodField()
    class Meta:
        model = Driver
        fields = [  'id',
                    'name',
                    'phone_number',
                    'description',
                    'zones',
                    'rating',
                  ]

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
                  ]

class DriverUpdateSerializer(serializers.ModelSerializer):

    class Meta:
        model = Driver
        fields = [  'isActive',
                    'isBusy',
                    'zones'
                  ]

class RequestSerializer(serializers.ModelSerializer):

    class Meta:
        model = Request
        fields = '__all__'