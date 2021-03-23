package org.jeecg.modules.ilang.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

fun Any.log(): Logger = LoggerFactory.getLogger(this.javaClass)
