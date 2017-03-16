from django.db import models

from driver.models import Driver
from passenger.models import Passenger
from django.contrib.contenttypes.models import ContentType
# Create your models here.

class IntegerRangeField(models.IntegerField):
    def __init__(self, verbose_name=None, name=None, min_value=None, max_value=None, **kwargs):
        self.min_value, self.max_value = min_value, max_value
        models.IntegerField.__init__(self, verbose_name, name, **kwargs)
    def formfield(self, **kwargs):
        defaults = {'min_value': self.min_value, 'max_value':self.max_value}
        defaults.update(kwargs)
        return super(IntegerRangeField, self).formfield(**defaults)

class Rating(models.Model):
    passenger = models.ForeignKey(Passenger)
    driver = models.ForeignKey(Driver)
    stars = IntegerRangeField(min_value=1, max_value=5)
    comment = models.CharField(max_length=150, blank=True)

class RatingManager(models.Model):
    def filter_by_instance(self, instance):
        obj_id = instance.id
        qs = super(RatingManager, self).filter(driver= obj_id)
        return qs