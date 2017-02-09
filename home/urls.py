from django.conf.urls import url
from django.contrib.auth import views as auth_views
from driver import views as drive_views

from . import views

urlpatterns = [
    url(r'^$', views.index, name='index'),

    url(r'^login$', auth_views.login, {'template_name': 'home/login.html'}, name='login'),

    url(r'^logout/$', auth_views.logout, {'next_page': 'login'}, name='logout'),

    url(r'^bedriver/$', drive_views.become_driver, name='bedriver')
]