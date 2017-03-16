"""SkreppIS URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url, include
from django.contrib import admin
from rest_framework.urlpatterns import format_suffix_patterns
import passenger.views
import driver.views
import ratings.views

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^passenger/$', passenger.views.PassengerList.as_view(), name='passenger-list'),
    url(r'^passenger/create/$', passenger.views.PassengerCreateView.as_view(), name='create'),
    url(r'^passenger/(?P<name>[\w-]+)/$', passenger.views.PassengerDetailView.as_view(), name='detail'),
    url(r'^passenger/(?P<name>[\w-]+)/edit$', passenger.views.PassengerUpdateView.as_view(), name='edit'),
    url(r'^passenger/(?P<name>[\w-]+)/delete$', passenger.views.PassengerDestroyView.as_view(), name='delete'),
    url(r'^driver/$', driver.views.DriverList.as_view(), name='driverlist'),
    url(r'^driver/create/$', driver.views.DriverCreateView.as_view(), name='drivercreate'),
    url(r'^driver/(?P<name>[\w-]+)/$', driver.views.DriverDetailView.as_view(), name='driverdetail'),
    url(r'^driver/(?P<name>[\w-]+)/edit$', driver.views.DriverUpdateView.as_view(), name='driveredit'),
    url(r'^driver/(?P<name>[\w-]+)/delete$', driver.views.DriverDestroyView.as_view(), name='driverdelete'),
    url(r'^ratings/$', ratings.views.RatingList.as_view(), name='ratinglist'),
    url(r'^ratings/(?P<id>$\d+)/$', ratings.views.RatingDetailView.as_view(), name='ratingdetail'),
    #url(r'^ratings/(?P<id>$\d+)/delete$', ratings.views.RatingDeleteView.as_view(), name='ratingdelete'),
    url(r'^accounts/', include('allaccess.urls')),
]

urlpatterns = format_suffix_patterns(urlpatterns)
