from django.contrib import admin

from .models import Driver, Zone, RideRequest

admin.site.register(Driver)
admin.site.register(Zone)
admin.site.register(RideRequest)
