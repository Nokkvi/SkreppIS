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
from rest_framework.filters import (
    SearchFilter,
    OrderingFilter,
)
from passenger.permissions import IsOwnerOrReadOnly
from .models import Rating
from .serializers import    (
                            RatingSerializer
                            )
from passenger.pagination import LimitOffsetPagination, PageNumberPagination
from django.views.decorators.csrf import csrf_exempt

# Lists all Passengers or creates a new one
#
class RatingList(ListAPIView):
    serializer_class = RatingSerializer
    permission_classes = [AllowAny]
    filter_backends = [SearchFilter, OrderingFilter]
    search_fields = ['passenger__name', 'driver__name']
    pagination_class = PageNumberPagination  # PageNumberPagination

    def get_queryset(self, *args, **kwargs):
        queryset_list = Rating.objects.all()
        query = self.request.GET.get("q")
        if query:
            queryset_list = queryset_list.filter(
                Q(passenger__name__contains=query)|
                Q(driver__name__contains=query)
            ).distinct()
        return queryset_list

class RatingDetailView(RetrieveAPIView):
    queryset = Rating.objects.all()
    serializer_class = RatingSerializer

    #lookup_field = "driver"
    permission_classes = [AllowAny]
