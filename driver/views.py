from django.shortcuts import get_object_or_404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Driver
from .serializers import DriverSerializer

# Lists all Passengers or creates a new one
#
class DriverList(APIView):

    def get(self, request):
        driver = Driver.objects.all()
        serializer = DriverSerializer(driver, many=True)
        return Response(serializer.data)

    def post(self):
        pass
