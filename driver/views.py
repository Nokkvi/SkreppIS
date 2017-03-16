from django.shortcuts import get_object_or_404
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Driver, Passenger
from .serializers import (
    DriverSerializer,
    DriverCreateSerializer,
    DriverDetailSerializer,
    DriverUpdateSerializer,
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
    permission_classes = [AllowAny]
    filter_backends = [SearchFilter, OrderingFilter]
    search_fields = ['zones__name', 'name']
    pagination_class = PageNumberPagination  # PageNumberPagination

    def get_queryset(self, *args, **kwargs):
        queryset_list = Passenger.objects.all()
        query = self.request.GET.get("q")
        if query:
            queryset_list = queryset_list.filter(
                Q(zones__name__contains=query)|
                Q(name__contains=query)
            ).distinct()
        return queryset_list

class DriverDetailView(RetrieveAPIView):
    queryset = Passenger.objects.all()
    serializer_class = DriverDetailSerializer
    lookup_field = "name"

    permission_classes = [AllowAny]


class DriverCreateView(CreateAPIView):
    queryset = Driver.objects.all()
    serializer_class = DriverCreateSerializer
    permission_classes = [IsAuthenticated]

    def perform_create(self, serializer):
        serializer.save(user=self.request.user, name=self.request.user.get_username(),
                        phone_number=Passenger.objects.get(user=self.request.user.pk).phone_number
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