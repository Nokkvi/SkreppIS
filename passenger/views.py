from django.shortcuts import get_object_or_404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from django.db.models import Q
from rest_framework.generics import (
    CreateAPIView,
    DestroyAPIView,
    ListAPIView,
    UpdateAPIView,
    RetrieveAPIView,
    RetrieveUpdateAPIView
    )

from rest_framework.permissions import (
    AllowAny,
    IsAuthenticated,
    IsAdminUser,
    IsAuthenticatedOrReadOnly,
)

from .permissions import IsOwnerOrReadOnly
from .models import Passenger
from .serializers import    (
                            PassengerSerializer,
                            PassengerDetailSerializer,
                            PassengerCreateUpdateSerializer
                            )
from django.views.decorators.csrf import csrf_exempt

# Lists all Passengers or creates a new one
#
class PassengerList(ListAPIView):
    queryset = Passenger.objects.all()
    serializer_class = PassengerSerializer
    permission_classes = [AllowAny]

    def get_queryset(self, *args, **kwargs):
        queryset_list = Passenger.objects.all()
        query = self.request.GET.get("q")
        if query:
            queryset_list = queryset_list.filter(
                Q(zones__name=query)|
                Q(name__contains=query)
            ).distinct()
        return queryset_list

class PassengerDetailView(RetrieveAPIView):
    queryset = Passenger.objects.all()
    serializer_class = PassengerDetailSerializer
    lookup_field = "name"
    permission_classes = [AllowAny]

class PassengerDestroyView(DestroyAPIView):
    queryset = Passenger.objects.all()
    serializer_class = PassengerDetailSerializer
    lookup_field = "name"
    permission_classes = [IsAuthenticated, IsOwnerOrReadOnly]

class PassengerUpdateView(UpdateAPIView):
    queryset = Passenger.objects.all()
    serializer_class = PassengerCreateUpdateSerializer
    lookup_field = "name"
    permission_classes = [IsAuthenticated, IsOwnerOrReadOnly]


class PassengerCreateView(CreateAPIView):
    queryset = Passenger.objects.all()
    serializer_class = PassengerCreateUpdateSerializer
    permission_classes = [IsAuthenticated]

    def perform_create(self, serializer):
        serializer.save(user=self.request.user, name=self.request.user.get_username(),
                        )