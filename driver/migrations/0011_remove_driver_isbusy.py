# -*- coding: utf-8 -*-
# Generated by Django 1.9 on 2017-04-04 14:09
from __future__ import unicode_literals

from django.db import migrations


class Migration(migrations.Migration):

    dependencies = [
        ('driver', '0010_remove_driver_requests'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='driver',
            name='isBusy',
        ),
    ]
