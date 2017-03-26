from django.shortcuts import get_object_or_404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Driver, Passenger, Zone
from .serializers import (
    DriverSerializer,
    DriverCreateSerializer,
    DriverDetailSerializer,
    DriverUpdateSerializer,
    ZoneSerializer,
    ZoneCreateSerializer,
    ZoneDetailSerializer,
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
    queryset = Driver.objects.all()
    serializer_class = DriverSerializer
    permission_classes = [IsAuthenticatedOrReadOnly]
    filter_backends = [SearchFilter, OrderingFilter]
    search_fields = ['name', 'zone__name', 'car_seats']
    pagination_class = PageNumberPagination  # PageNumberPagination

    def get_queryset(self, *args, **kwargs):
        queryset_list = Driver.objects.all()
        query = self.request.GET.get("q")
        if query:
            queryset_list = queryset_list.filter(
                (Q(zone__name__exact=query)|
                Q(name__contains=query))
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
                        phone_number=Driver.objects.get(user=self.request.user.pk).phone_number
                        )

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
    pagination_class = PageNumberPagination

    def perform_create(self, serializer):
        serializer.save(driver=Driver.objects.get(user=self.request.user.pk),
                        )

class ZoneDestroyView(DestroyAPIView):
    serializer_class = ZoneDetailSerializer(many=True)
    permission_classes = [IsOwnerOrReadOnly]
    lookup_field = "driver"

    def get_queryset(self, *args, **kwargs):
        queryset_list = Zone.objects.filter(driver=Driver.objects.get(user=self.request.user.pk))
        queryset_list.delete()
        return queryset_list