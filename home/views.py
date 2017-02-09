# coding=utf-8
from django.http import HttpResponse
from django.shortcuts import render, redirect
from django.views.generic import View
from .forms import LoginForm
from django.contrib.auth import authenticate, login
from django.views.decorators.csrf import csrf_protect
from django.contrib.auth.decorators import login_required
from django.contrib.auth import logout


def index(request):
    context = {}
    return render(request, 'home/index.html', context)
    # return HttpResponse("<h1>whaht</h1>")


class LoginFormView(View):
    form_class = LoginForm
    template_name = 'home/login.html'

    def get(self, request):
        form = self.form_class(None)
        return render(request, self.template_name, {'form': form})

    def post(self, request):
        form = self.form_class(request.POST)

        if form.is_valid():

            username = form.cleaned_data['username']
            password = form.cleaned_data['password']

            # returns User objects if credentials are correct
            user = authenticate(username=username, password=password)

            if user is not None:

                if user.is_active:
                    login(request, user)
                    return redirect('../passenger')

        return render(request, self.template_name, {'form': form})