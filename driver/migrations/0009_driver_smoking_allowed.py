# -*- coding: utf-8 -*-
# Generated by Django 1.9 on 2017-03-26 17:28
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('driver', '0008_driver_car_seats'),
    ]

    operations = [
        migrations.AddField(
            model_name='driver',
            name='smoking_allowed',
            field=models.BooleanField(default=False),
        ),
    ]
