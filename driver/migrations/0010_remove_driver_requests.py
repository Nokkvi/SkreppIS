# -*- coding: utf-8 -*-
# Generated by Django 1.9 on 2017-04-03 17:45
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('driver', '0009_driver_smoking_allowed'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='driver',
            name='requests',
        ),
    ]
