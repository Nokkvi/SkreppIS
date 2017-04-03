from django.contrib import admin

from .models import Passenger, RideRequest

admin.site.register(Passenger)


admin.site.register(RideRequest)
