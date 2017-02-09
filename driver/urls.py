from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^$', views.setZonesView.as_view(), name='index'),
]