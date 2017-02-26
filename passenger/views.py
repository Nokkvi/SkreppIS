from django.shortcuts import get_object_or_404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Passenger
from .serializers import PassengerSerializer

# Lists all Passengers or creates a new one
#
class PassengerList(APIView):

    def get(self, request):
        passenger = Passenger.objects.all()
        serializer = PassengerSerializer(passenger, many=True)
        return Response(serializer.data)

    def post(self):
        serializer = PassengerSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)