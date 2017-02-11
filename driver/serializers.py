from rest_framework import serializers

from .models import Driver

class DriverSerializer(serializers.ModelSerializer):

    class Meta:
        model = Driver
        fields = '__all__'

class RequestSerializer(serializers.ModelSerializer):

    class Meta:
        model = Driver
        fields = '__all__'