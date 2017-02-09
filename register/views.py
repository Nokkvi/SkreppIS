# coding=utf-8
from django.http import HttpResponse
from django.template import loader
from django.contrib.auth import authenticate, login
from django.shortcuts import render, redirect
from django.views.generic import View
from .forms import UserForm, PassengerForm
from passenger.models import Passenger


class UserFormView(View):
    user_form_class = UserForm
    passenger_form_class = PassengerForm
    template_name = 'register/index.html'

    def get(self, request):
        user_form = self.user_form_class(None)
        passenger_form = self.passenger_form_class(None)
        return render(request, self.template_name, {
            'user_form': user_form,
            'passenger_form': passenger_form
        })

    def post(self, request):
        user_form = self.user_form_class(request.POST)
        passenger_form = self.passenger_form_class(request.POST)

        if user_form.is_valid() and passenger_form.is_valid():

            user = user_form.save(commit=False)

            username = user_form.cleaned_data['username']
            password = user_form.cleaned_data['password']
            phone_number = passenger_form.cleaned_data['phone_number']
            user.set_password(password)
            user.save()

            # returns User objects if credentials are correct
            user = authenticate(username=username, password=password)
            Passenger.objects.create_passenger(phone_number, user)

            if user is not None:

                if user.is_active:
                    login(request, user)
                    return redirect('../passenger')

        return render(request, self.template_name, {
            'user_form': user_form,
            'passenger_form': passenger_form
        })

# Create your views here.
