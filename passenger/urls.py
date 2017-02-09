from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^$', views.SearchFormView.as_view(), name='index'),
]
