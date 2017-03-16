from rest_framework import serializers

from .models import Passenger

class PassengerSerializer(serializers.ModelSerializer):

    class Meta:
        model = Passenger
        fields = [  'id',
                    'name',
                    'phone_number'
                  ]

class PassengerDetailSerializer(serializers.ModelSerializer):

    class Meta:
        model = Passenger
        fields = [  'id',
                    'name',
                    'phone_number'
                  ]


class PassengerCreateUpdateSerializer(serializers.ModelSerializer):

    class Meta:
        model = Passenger
        fields = [#'id',
                  #'name',
                  'phone_number'
                  ]