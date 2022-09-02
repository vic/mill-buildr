package buildr

import mill._

trait ProjectTwo extends ProjectOne {
  def two() = T.command[Unit] { pprint.pprintln("Two") }
}
