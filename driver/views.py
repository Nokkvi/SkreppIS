from django.shortcuts import get_object_or_404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Driver
from .serializers import DriverSerializer
from django.views.decorators.csrf import csrf_exempt

# Lists all Passengers or creates a new one
#
class DriverList(APIView):

    def get(self, request):
        driver = Driver.objects.all()
        serializer = DriverSerializer(driver, many=True)
        return Response(serializer.data)

    @csrf_exempt
    def post(self, request):
        serializer = DriverSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
