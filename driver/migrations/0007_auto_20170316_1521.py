# -*- coding: utf-8 -*-
# Generated by Django 1.10.3 on 2017-03-16 15:21
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('driver', '0006_auto_20170316_1450'),
    ]

    operations = [
        migrations.AlterField(
            model_name='zone',
            name='driver',
            field=models.ForeignKey(blank=True, null=True, on_delete=django.db.models.deletion.CASCADE, to='driver.Driver'),
        ),
    ]
