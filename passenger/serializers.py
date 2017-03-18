from rest_framework import serializers
from rest_framework.fields import SerializerMethodField
from rest_framework.serializers import HyperlinkedIdentityField

from .models import Passenger, Zone



class PassengerSerializer(serializers.ModelSerializer):
    url = HyperlinkedIdentityField(
        view_name='detail',
        lookup_field='name',
    )
    zones = SerializerMethodField()
    class Meta:
        model = Passenger
        fields = [  'url',
                    'name',
                    'phone_number',
                    'zones',
        ]

    def get_zones(self, obj):
        c_qs = Zone.objects.filter(passenger__name=obj)
        zones = ZoneSerializer(c_qs, many=True).data
        return zones

class PassengerDetailSerializer(serializers.ModelSerializer):
    image = SerializerMethodField()
    class Meta:
        model = Passenger
        fields = [  'name',
                    'phone_number',
                    'description',
                    'image',
                  ]

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

class ZoneSerializer(serializers.ModelSerializer):
    passenger = SerializerMethodField()
    class Meta:
        model = Zone
        fields = [
            'name',
            'passenger',
        ]

    def get_passenger(self, obj):
        return obj.passenger.name