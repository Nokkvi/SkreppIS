from django.shortcuts import get_object_or_404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Driver, Passenger, Zone, RideRequest
from .serializers import (
    DriverSerializer,
    DriverCreateSerializer,
    DriverDetailSerializer,
    DriverUpdateSerializer,
    DriverToggleActiveSerializer,
    ZoneSerializer,
    ZoneCreateSerializer,
    ZoneDetailSerializer,
    RideRequestSerializer,
    RideRequestCreateSerializer
)
from django.views.decorators.csrf import csrf_exempt
from rest_framework.generics import (
    CreateAPIView,
    DestroyAPIView,
    ListAPIView,
    UpdateAPIView,
    RetrieveAPIView,
    RetrieveUpdateAPIView,
    )

from rest_framework.permissions import (
    AllowAny,
    IsAuthenticated,
    IsAdminUser,
    IsAuthenticatedOrReadOnly,
)

from rest_framework.filters import (
    SearchFilter,
    OrderingFilter,
)
from passenger.pagination import PageNumberPagination
from .permissions import IsOwnerOrReadOnly
from django.db.models import Q
# Lists all Passengers or creates a new one
#
class DriverList(ListAPIView):
    queryset = Driver.objects.filter(isActive=True)
    serializer_class = DriverSerializer
    permission_classes = [IsAuthenticatedOrReadOnly]
    filter_backends = [SearchFilter, OrderingFilter]
    search_fields = ['zone__name']
    pagination_class = PageNumberPagination  # PageNumberPagination

    def get_queryset(self, *args, **kwargs):
        queryset_list = Driver.objects.filter(isActive=True)
        query = self.request.GET.get("q")
        if query:
            queryset_list = queryset_list.filter(
                Q(car_seats__gte=query)
            )
        return queryset_list

class DriverDetailView(RetrieveAPIView):
    queryset = Driver.objects.all()
    serializer_class = DriverDetailSerializer
    lookup_field = "name"

    permission_classes = [IsAuthenticatedOrReadOnly]


class DriverCreateView(CreateAPIView):
    queryset = Driver.objects.all()
    serializer_class = DriverCreateSerializer
    permission_classes = [IsAuthenticatedOrReadOnly]

    def perform_create(self, serializer):
        serializer.save(user=self.request.user, name=self.request.user.get_username(),
                        phone_number=Passenger.objects.get(user=self.request.user.pk).phone_number
                        )


class DriverToggleActiveView(UpdateAPIView):
    queryset = Driver.objects.all()
    serializer_class = DriverToggleActiveSerializer
    lookup_field = "name"
    permission_classes = [IsAuthenticatedOrReadOnly]


class DriverDestroyView(DestroyAPIView):
    queryset = Driver.objects.all()
    serializer_class = DriverDetailSerializer
    lookup_field = "name"
    permission_classes = [IsAuthenticated, IsOwnerOrReadOnly]

class DriverUpdateView(UpdateAPIView):
    queryset = Driver.objects.all()
    serializer_class = DriverUpdateSerializer
    lookup_field = "name"
    permission_classes = [IsAuthenticated, IsOwnerOrReadOnly]


def perform_update(self, serializer):
    serializer.save(phone_number=Passenger.objects.get(user=self.request.user.pk).phone_number
                    )

class ZoneList(ListAPIView):
    queryset = Zone.objects.all()
    serializer_class = ZoneSerializer
    permission_classes = [IsAuthenticatedOrReadOnly]
    filter_backends = [SearchFilter, OrderingFilter]
    search_fields = ['name', 'driver__name']
    pagination_class = PageNumberPagination  # PageNumberPagination

    def get_queryset(self, *args, **kwargs):
        queryset_list = Zone.objects.all()
        query = self.request.GET.get("q")
        if query:
            queryset_list = queryset_list.filter(
                Q(name__contains=query) |
                Q(driver__name__contains=query)
            ).distinct()
        return queryset_list

class ZoneCreateView(CreateAPIView):
    queryset = Zone.objects.all()
    serializer_class = ZoneCreateSerializer
    permission_classes = [IsAuthenticatedOrReadOnly]
    lookup_field = "driver"
    pagination_class = PageNumberPagination

    def perform_create(self, serializer):
        serializer.save(driver=Driver.objects.get(user=self.request.user.id),
                        )

class ZoneDestroyView(DestroyAPIView):
    serializer_class = ZoneDetailSerializer(many=True)
    permission_classes = [IsAuthenticatedOrReadOnly]
    lookup_field = "driver"

    def get_queryset(self, *args, **kwargs):
        queryset_list = Zone.objects.filter(driver=Driver.objects.get(user=self.request.user.pk))
        queryset_list.delete()
        return queryset_list

class RideRequestList(ListAPIView):
    queryset = RideRequest.objects.all()
    serializer_class = RideRequestSerializer
    permission_classes = [IsAuthenticatedOrReadOnly]
    filter_backends = [SearchFilter, OrderingFilter]
    pagination_class = PageNumberPagination  # PageNumberPagination

    def get_queryset(self, *args, **kwargs):
        queryset_list = RideRequest.objects.all()
        query = self.request.GET.get("q")
        if query:
            queryset_list = queryset_list.filter(
                Q(start=query)
            )
        return queryset_list

class RideRequestCreateView(CreateAPIView):
    queryset = RideRequest.objects.all()
    serializer_class = RideRequestCreateSerializer
    permission_classes = [IsAuthenticatedOrReadOnly]
    pagination_class = PageNumberPagination

    def perform_create(self, serializer):
        serializer.save(passenger=Passenger.objects.get(user=self.request.user.pk),
                        )