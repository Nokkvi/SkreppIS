from rest_framework import serializers
from rest_framework.serializers import HyperlinkedIdentityField, SerializerMethodField

from ratings.models import Rating

class RatingSerializer(serializers.ModelSerializer):
    driver = SerializerMethodField()
    class Meta:
        model = Rating
        fields = [
            'driver',
            'passenger',
            'stars',
            'comment',
        ]

    def get_driver(self, obj):
        return obj.driver.name


class RatingDetailSerializer(serializers.ModelSerializer):
    driver = SerializerMethodField()
    passenger = SerializerMethodField()
    class Meta:
        model = Rating
        fields = [
            'driver',
            'passenger',
            'stars',
            'comment',
        ]

    def get_driver(self, obj):
        return obj.driver.name