# coding=utf-8
from django.http import HttpResponse
from django.shortcuts import render
from driver.models import Driver, Zone
from driver.forms import setZonesForm
from django.views.generic import View
import logging


def index(request):
    return render(request, './driver/index.html')


def become_driver(request):
    user = request.user
    Driver.objects.create_driver(user)
    return render(request, './driver/index.html')


class setZonesView(View):
    form_class = setZonesForm
    template_name = './driver/index.html'


    def get(self, request):
        form = self.form_class(None)
        found_zones = Zone.objects.all()
        return render(request, self.template_name, {'form': form, 'queryset': found_zones})


    def post(self, request):
            driver = Driver.objects.get(user=request.user)
            form = self.form_class(request.POST, instance=driver)
            if form.is_valid():
               driver = form.save(commit=False)
               driver.user = request.user
               driver.name = request.user.get_full_name()
               driver.save()
               form.save_m2m()
               #Gera eitthvað til að setja zones inn í töflu)
               # #logging.warning(self.id)
               return render(request, self.template_name, {'form': form})



