# -*- coding: utf-8 -*-
# Generated by Django 1.9 on 2017-04-05 21:10
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('driver', '0014_auto_20170405_1136'),
    ]

    operations = [
        migrations.AlterField(
            model_name='riderequest',
            name='driver',
            field=models.CharField(blank=True, max_length=100, null=True),
        ),
    ]