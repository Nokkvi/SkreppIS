#coding:utf-8
from django.contrib.auth.models import User
from django import forms
from driver.models import Driver, Zone

LOCATIONS = (
    ('Breiðholt', 'Breiðholt'),
    ('Washington', 'Washington'),
    ('Moskva', 'Moskva'),
    ('Garðabær', 'Garðabær'),
    ('Hafnafjörður', 'Hafnafjörður'),
    ('Kópavogur', 'Kópavogur'),
    ('Mosfellsbær', 'Mosfellsbær'),
    ('Seltjarnarnes', 'Seltjarnarnes'),
    ('Miðbær', 'Miðbær')
)

class SearchForm(forms.ModelForm):
    zoneFrom = forms.ChoiceField(choices=LOCATIONS, required=True, label="Where from?" )
    zoneTo = forms.ChoiceField(choices=LOCATIONS, required=True, label="Where to?" )

    class Meta:
        model = Driver
        fields = ['zoneFrom', 'zoneTo']

