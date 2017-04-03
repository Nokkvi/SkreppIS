from django.contrib import admin

from .models import Passenger, Zone, RideRequest

admin.site.register(Passenger)

admin.site.register(Zone)

admin.site.register(RideRequest)
