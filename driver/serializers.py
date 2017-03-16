from rest_framework import serializers

from .models import Driver, Request

class DriverSerializer(serializers.ModelSerializer):

    class Meta:
        model = Driver
        fields = [  'id',
                    'user',
                    'name',
                    'phone_number'
                  ]

class DriverDetailSerializer(serializers.ModelSerializer):

    class Meta:
        model = Driver
        fields = [  'id',
                    'name',
                    'phone_number'
                  ]

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