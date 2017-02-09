from django.contrib import admin

from driver.models import Driver, Zone, Request

admin.site.register(Driver)
admin.site.register(Zone)
admin.site.register(Request)

# Register your models here.
