# -*- coding: utf-8 -*-
# Generated by Django 1.9 on 2017-04-02 23:10
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('passenger', '0008_riderequest_seats'),
    ]

    operations = [
        migrations.AddField(
            model_name='riderequest',
            name='over',
            field=models.BooleanField(default=False),
        ),
    ]
