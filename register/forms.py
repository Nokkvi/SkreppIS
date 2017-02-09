from django.contrib.auth.models import User
from passenger.models import Passenger
from django import forms


class UserForm(forms.ModelForm):
    password = forms.CharField(widget=forms.PasswordInput)

    class Meta:
        model = User
        fields = ['username', 'first_name', 'last_name', 'email', 'password']

class PassengerForm(forms.ModelForm):

    class Meta:
        model = Passenger
        fields = ['phone_number']
