# -*- coding: utf-8 -*-
# Generated by Django 1.9 on 2017-04-02 23:03
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('passenger', '0007_riderequest'),
    ]

    operations = [
        migrations.AddField(
            model_name='riderequest',
            name='seats',
            field=models.IntegerField(null=True),
        ),
    ]
