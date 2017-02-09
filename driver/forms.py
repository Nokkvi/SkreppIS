from django.contrib.auth.models import User
from django import forms
from driver.models import Driver, Zone
from django.forms.widgets import CheckboxSelectMultiple


class setZonesForm(forms.ModelForm):
    class Meta:
        model = Driver
        fields = ['zones']


