package buildr

import mill._

trait ProjectOne extends Module {
  def one() = T.command[Unit] { pprint.pprintln("ONE") }
}
