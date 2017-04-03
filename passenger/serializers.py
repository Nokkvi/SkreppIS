from rest_framework import serializers
from rest_framework.fields import SerializerMethodField
from rest_framework.serializers import HyperlinkedIdentityField

from .models import Passenger, RideRequest



class PassengerSerializer(serializers.ModelSerializer):
    url = HyperlinkedIdentityField(
        view_name='detail',
        lookup_field='name',
    )
    riderequests = SerializerMethodField()
    class Meta:
        model = Passenger
        fields = [  'url',
                    'name',
                    'phone_number',
                    'riderequests',
        ]

    def get_riderequests(self, obj):
        c_qs = RideRequest.objects.filter(passenger__name=obj)
        riderequests = RideRequestSerializer(c_qs, many=True).data
        return riderequests

class PassengerDetailSerializer(serializers.ModelSerializer):
    image = SerializerMethodField()
    riderequest = SerializerMethodField()
    class Meta:
        model = Passenger
        fields = [  'name',
                    'phone_number',
                    'description',
                    'image',
                    'riderequests',
                  ]
    def get_riderequests(self, obj):
        c_qs = RideRequest.objects.filter(passenger__name=obj)
        riderequests = RideRequestSerializer(c_qs, many=True).data
        return riderequests

    def get_image(self, obj):
        try:
            image = obj.image.url
        except:
            image = None
        return image


class PassengerCreateSerializer(serializers.ModelSerializer):

    class Meta:
        model = Passenger
        fields = [#'id',
                  #'name',
                  'phone_number',
                  ]

class PassengerUpdateSerializer(serializers.ModelSerializer):

    class Meta:
        model = Passenger
        fields = [#'id',
                  #'name',
                  'phone_number',
                  'description',
                  ]

class RideRequestSerializer(serializers.ModelSerializer):
    passenger = SerializerMethodField()
    class Meta:
        model = RideRequest
        fields = [
            'passenger',
            'start',
            'end',
            'over',
        ]

    def get_passenger(self, obj):
        return obj.passenger.name


class RideRequestCreateSerializer(serializers.ModelSerializer):
    class Meta:
        model = RideRequest
        fields = [
            'start',
            'end',
            'seats',
        ]
